(function () {

  'use strict';

  angular.module('nw-admin').controller('CategoryCtrl', function ($http) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      $http
        .get('/api/category')
        .then(function(response){
          ctrl.categories = response.data;
        });
    }

    ctrl.saveCategory = function(){
      if(ctrl.entity && ctrl.entity.code){
        delete ctrl.error;
        $http
          .post('/api/category', ctrl.entity)
          .then(function(response){
            ctrl.entity = response.data;
          })
          .catch(function(response){
            switch(response.status){
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

    ctrl.entity = {};
    refresh();
  });

})();