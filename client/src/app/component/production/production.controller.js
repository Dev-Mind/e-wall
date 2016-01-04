(function () {

  'use strict';

  angular.module('ew-admin').controller('ProductionCtrl', function ($http, $state, $stateParams, SecurityService) {
    'ngInject';

    var ctrl = this;

    $http
      .get('/api/public/category')
      .then(function (response) {
        ctrl.categories = response.data;
      });

    if ($stateParams.id) {
      //We are in edition mode and we want to load the production for this id
      $http.get('/api/public/production/' + $stateParams.id)
        .then(function (response) {
          ctrl.production = response.data;
          ctrl.currentTab = 'content';

          if (response.data.qrcodes && response.data.qrcodes[0] && response.data.qrcodes[0].category) {
            $http.get('/api/public/category/' + response.data.qrcodes[0].category.id)
              .then(function (response) {
                ctrl.category = response.data;
              });
          }
        });
    }

    //Select a category on the first tab
    ctrl.selectCategory = function (category) {
      ctrl.currentTab = 'content';
      ctrl.category = category;
    };

    //Go back on the first tab
    ctrl.changeCategory = function () {
      delete ctrl.currentTab;
    };

    ctrl.cancel = function () {
      $state.go('admin');
    };

    ctrl.saveProduction = function () {
      if (ctrl.category && ctrl.production) {
        delete ctrl.error;
        $http
          .post('/api/secured/category/' + ctrl.category.id + '/production', ctrl.production)
          .then(function () {
            $state.go('admin');
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

    SecurityService.isAdmin(function(response){
      ctrl.isAdmin = response;
    });
  });

})();