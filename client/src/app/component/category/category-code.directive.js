(function () {

  'use strict';

  angular.module('ew-admin').directive('uniqueCategoryCode', function ($http, $rootScope, $q) {

    'ngInject';

    return {
      require: 'ngModel',
      restrict: 'A',
      scope: {
        id: '='
      },
      link: function (scope, element, attributes, ngModelController) {

        ngModelController.$asyncValidators.uniqueCategoryCode = function (modelValue, viewValue) {
          var deferred = $q.defer();

          if (!viewValue) {
            deferred.resolve(true);
          }
          else {
            // Lookup user by username
            $http.get('/api/category/check/' + viewValue + (scope.id ? '?id=' + scope.id : ''), {ignoreErrorRedirection: 'ignoreErrorRedirection'})
              .then(function (response) {
                //if code is not used it's OK
                deferred.resolve(true);
              })
              .catch(function () {
                deferred.reject('exists');
              });
          }
          return deferred.promise;
        };
      }
    };
  });


})();
