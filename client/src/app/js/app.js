(function () {

  'use strict';

  angular.module('numeric-wall', [
    'ui.router',
    'ngSanitize'
   ]);

  /**
   * On startup we read info on application
   */
  angular.module('numeric-wall').run(function($http, $rootScope) {
    $http.get('/api/about').then(function(response){
      $rootScope.numericwall = response.data;
    });
  });
})();


