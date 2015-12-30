(function () {

  'use strict';

  angular.module('ew-admin').controller('ProductionsCtrl', function ($http, $state, $uibModal) {
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

    ctrl.update = function(production){
      $state.go('production', {id : production.id});
    };

    ctrl.delete = function(production){

      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'deleteProduction.html',
        controller: 'DeleteProductionCtrl',
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
            refresh();
          })
          .catch(function(response){
            switch(response.status){
              default:
                ctrl.error = 'Une erreur a été détectée lors de la suppression';
            }
          });
      });


    };

  });

  angular.module('ew-admin').controller('DeleteProductionCtrl', function ($scope, $uibModalInstance, production) {
    'ngInject';

    $scope.selected = production;

    $scope.ok = function () {
      $uibModalInstance.close();
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };
  });
})();