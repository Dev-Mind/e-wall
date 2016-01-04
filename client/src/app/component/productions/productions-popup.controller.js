(function () {

  'use strict';

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