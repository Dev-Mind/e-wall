(function () {

  'use strict';

  angular.module('ew-admin').controller('CategoryCtrl', function ($http) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      delete ctrl.error;
      delete ctrl.entity;

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
          .then(function(){
            refresh();
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

    ctrl.add = function(){
      ctrl.entity = {};
    };

    ctrl.cancel = function(){
      delete ctrl.entity;
    };

    ctrl.update = function(id){
      $http
        .get('/api/category/' + id)
        .then(function(response){
          ctrl.entity = response.data;
        });
    };

    ctrl.delete = function(id){
      $http
        .delete('/api/category/' + id)
        .then(function(response){
          refresh();
        })
        .catch(function(response){
          switch(response.status){
            case 507:
              ctrl.error = 'Erreur lors de la suppressioj des QR codes';
              break;
            default:
              ctrl.error = 'Une erreur a été détectée lors de la suppression';
          }
        });
    };

    refresh();
  });

})();