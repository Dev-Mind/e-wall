(function () {

  'use strict';

  angular.module('ew-admin').controller('ProductionCategoryCtrl', function ($http, $state, $stateParams, $uibModal, SecurityService) {
    'ngInject';

    var ctrl = this;

    ctrl.mode = 'category';

    $http
      .get('/api/public/production/' + $stateParams.code + '/general')
      .then(function (response) {
        ctrl.production =  response.data;
        if(!ctrl.production.author){
          $http
            .get('api/public/connected')
            .then(function(response){
              ctrl.production.author = response.data.emseid;
            });
        }
      });



    ctrl.cancel = function () {
      $state.go('category');
    };

    ctrl.saveProduction = function () {
      if (ctrl.production) {
        delete ctrl.error;
        $http
          .post('/api/secured/category/' + ctrl.production.qrcodes[0].category.id + '/production', ctrl.production)
          .then(function () {
            $state.go('category');
          })
          .catch(function (response) {
            switch (response.status) {
              case 400:
                ctrl.error = 'Les données saisies sont incorrectes ' + response.data ? response.data.message : '';
                break;
              default:
                ctrl.error = 'Une erreur a été détectée lors de l\'enregistrement';
            }
          });
      }
    };

    ctrl.seeProduction = function(){
      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'seeProduction.html',
        controller: 'SeeProductionCtrl',
        size:'lg',
        resolve: {
          production: function () {
            return ctrl.production;
          }
        }
      });
    };

  });

})();