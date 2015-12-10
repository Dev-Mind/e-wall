(function () {

  'use strict';

  angular.module('nw').config(function($httpProvider) {
    $httpProvider.interceptors.push('errorInterceptor', 'spinnerInterceptor');
  });

  angular.module('nw-structure').factory('errorInterceptor', function ($rootScope, $q) {
    'ngInject';

    function isFunctionalError(response) {
      return response.headers && response.headers('Content-Type') &&
        response.headers('Content-Type').indexOf('application/json') === 0 &&
        angular.isDefined(response.data.message);
    }

    return {
      responseError: function(response){
        if ((response.status === 401 || response.status === 403) && !response.config.ignoreErrorRedirection) {
          var deferred = $q.defer();
          $rootScope.$emit('$nwError', {type : 'RIGHTS'});
          return deferred.promise;
        }
        else if (!isFunctionalError(response)) {
          $rootScope.$emit('$nwError', response);
        }
        return $q.reject(response);
      }
    };
  });

  /**
   * This interceptor is used to display a spinner when a request is executed
   */
  angular.module('nw-structure').factory('spinnerInterceptor', function ($rootScope, $q) {
    'ngInject';

    return {
      request: function(config) {
        $rootScope.spinner = 'on';
        return config;
      },

      requestError: function(rejection) {
        $rootScope.spinner = 'off';
        return $q.reject(rejection);
      },

      response: function(response) {
        $rootScope.spinner = 'off';
        return response;
      },

      responseError: function(rejection) {
        $rootScope.spinner = 'off';
        return $q.reject(rejection);
      }
    };
  });


})();