(function () {

  'use strict';

  angular.module('ew-admin').controller('ProductionCtrl', function ($http, $state) {
    'ngInject';

    var ctrl = this;

    $http
      .get('/api/public/category')
      .then(function(response){
        ctrl.categories = response.data;
      });


    ctrl.selectCategory = function(category){
      ctrl.currentTab = 'content';
      ctrl.category = category;
      ctrl.production = {};
    };

    ctrl.cancel = function(){
      delete ctrl.currentTab;
      delete ctrl.category;
      delete ctrl.production;
    };

    ctrl.saveProduction = function(){
      if(ctrl.category && ctrl.production){
        delete ctrl.error;
        $http
          .post('/api/secured/' + ctrl.category.id + '/production', ctrl.production)
          .then(function(){
            $state.go('home');
          })
          .catch(function(response){
            switch(response.status){
              case 400:
                ctrl.error = 'Les données saisies sont incorrectes ' + response.data ? response.data.message : '';
                break;
              default:
                ctrl.error = 'Une erreur a été détectée lors de l\'enregistrement';
            }
          });
      }
    };
  });

})();