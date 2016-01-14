(function () {

  'use strict';

  angular.module('ew-admin').controller('CategoryCtrl', function ($http, $uibModal, $timeout, $state) {
    'ngInject';

    var ctrl = this;

    function refresh() {
      delete ctrl.error;
      delete ctrl.entity;
      delete ctrl.categoryQRCode;
      $http
        .get('/api/public/category')
        .then(function (response) {
          ctrl.categories = response.data;
        });
    }

    ctrl.saveCategory = function () {
      if (ctrl.entity && ctrl.entity.code) {
        delete ctrl.error;
        $http
          .post('/api/secured/category', ctrl.entity)
          .then(function () {
            refresh();
          })
          .catch(function (response) {
            switch (response.status) {
              case 400:
                ctrl.error = 'Les données saisies sont incorrectes ' + response.data ? response.data.message : '';
                break;
              case 507:
                ctrl.error = 'Erreur lors de la génération des QR codes';
                break;
              default:
                ctrl.error = 'Une erreur a été détectée lors de l\'enregistrement';
            }
          });
      }
    };

    ctrl.add = function () {
      delete ctrl.error;
      ctrl.entity = {};
    };

    ctrl.cancel = function () {
      delete ctrl.entity;
    };

    ctrl.update = function (id) {
      delete ctrl.error;
      ctrl.categoryQRCode = {};

      $http
        .get('/api/public/category/' + id)
        .then(function (response) {
          ctrl.entity = response.data;

          //We need to read all the QR code to know which are linked to a production
          var i = 0;
          ctrl.entity.qrcodes.forEach(function (elt) {
            if (elt.big) {
              ctrl.categoryQRCode.path = elt.svgPath;

              $timeout(function () {
                var svg = document.querySelector('.qrCode');
                var container = document.querySelector('.qrCodeContainer');
                ctrl.categoryQRCode.dimension = container.innerWidth || container.clientWidth * 0.93;
                ctrl.categoryQRCode.scaleRatio = ctrl.categoryQRCode.dimension / elt.dimension;
              }, 50);
            }
            else if (!!elt.production) {
              i++;
            }
          });
          ctrl.nbProd = i;
        });
    };

    ctrl.delete = function (category) {

      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'deleteCategory.html',
        controller: 'DeleteCategoryCtrl',
        size:'lg',
        resolve: {
          category: function () {
            return category;
          }
        }
      });

      modalInstance.result.then(function () {
        $http
          .delete('/api/secured/category/' + category.id)
          .then(function (response) {
            refresh();
          })
          .catch(function (response) {
            switch (response.status) {
              case 507:
                ctrl.error = 'Erreur lors de la suppression des QR codes';
                break;
              default:
                ctrl.error = 'Une erreur a été détectée lors de la suppression';
            }
          });
      });
    };

    ctrl.addProduction = function (category) {
      $state.go('productioncat', {code: category.code});
    };

    refresh();


  });

  angular.module('ew-admin').controller('DeleteCategoryCtrl', function ($scope, $uibModalInstance, category) {
    'ngInject';

    $scope.selected = category;

    $scope.ok = function () {
      $uibModalInstance.close();
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };
  });

})();