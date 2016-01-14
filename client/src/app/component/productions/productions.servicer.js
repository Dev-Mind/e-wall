(function () {

  'use strict';

  angular.module('ew-admin').factory('ProductionService', function ($http, $state, $uibModal) {
    'ngInject';


    function updateProduction(production){
      $state.go('production', {id : production.id});
    }

    function deleteProduction(production, callBack){

      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'deleteProduction.html',
        controller: 'DeleteProductionCtrl',
        size:'lg',
        resolve: {
          production: function () {
            return production;
          }
        }
      });

      modalInstance.result.then(function () {
        $http
          .delete('/api/secured/production/' + production.id)
          .then(function(response){
            callBack();
          })
          .catch(function(response){
            switch(response.status){
              default:
                ctrl.error = 'Une erreur a été détectée lors de la suppression';
            }
          });
      });
    }

    return{
      updateProduction : updateProduction,
      deleteProduction : deleteProduction
    };
  });

})();