(function () {

  'use strict';

  angular.module('ew-admin').controller('SeeProductionCtrl', function ($scope, $uibModalInstance, production) {
    'ngInject';

    $scope.selected = production;

    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };
  });


})();
