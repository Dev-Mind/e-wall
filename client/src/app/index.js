(function () {

  'use strict';

  angular.module('nw-structure', []);

  angular.module('nw', [
    'ui.router',
    'ngSanitize',
    'nw-structure'
   ]);

  /**
   * On startup we read info on application
   */
  angular.module('nw').run(function($http, $rootScope) {
    $http.get('/api/about').then(function(response){
      $rootScope.numericwall = response.data;
    });
  });
})();


