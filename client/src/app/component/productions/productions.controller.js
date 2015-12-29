(function () {

  'use strict';

  angular.module('ew-admin').controller('ProductionsCtrl', function ($http, $state) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      $http
        .get('/api/secured/production/myself')
        .then(function(response){
          ctrl.productions = response.data;
        });
    }

    refresh();

    ctrl.update = function(id){
      $state.go('production', {id : id});
    };

    ctrl.delete = function(id){
      $http
        .delete('/api/secured/production/' + id)
        .then(function(response){
          refresh();
        })
        .catch(function(response){
          switch(response.status){
            default:
              ctrl.error = 'Une erreur a été détectée lors de la suppression';
          }
        });
    };

  });

})();