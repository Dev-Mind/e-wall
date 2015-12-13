(function () {

  'use strict';

  angular.module('nw-structure', ['ui.router', 'nw-templates']);
  angular.module('nw-admin', ['nw-templates']);
  angular.module('nw-qrcode', ['nw-templates']);

  angular.module('nw', [
    'ui.router',
    'ui.bootstrap',
    'ngSanitize',
    'nw-structure',
    'nw-admin',
    'nw-qrcode'
   ]);

  /**
   * On startup we read info on application
   */
  angular.module('nw').run(function($http, $rootScope) {
    $http.get('/api/about').then(function(response){
      $rootScope.numericwall = response.data;
    });
  });


  angular.module('nw').constant('qrCodeParameters', {
    qrcode_margin: 6,
    qrcode_foreground: '#000000',
    qrcode_background: '#ffffff'
  });

})();


