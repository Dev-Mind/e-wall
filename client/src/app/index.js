(function () {

  'use strict';

  angular.module('ew-structure', ['ui.router', 'ew-templates']);
  angular.module('ew-admin', ['ew-templates']);
  angular.module('ew-qrcode', ['ew-templates']);

  angular.module('ew', [
    'ui.router',
    'ui.bootstrap',
    'ngSanitize',
    'ew-structure',
    'ew-admin',
    'ew-qrcode'
   ]);

  /**
   * On startup we read info on application
   */
  angular.module('ew').run(function($http, $rootScope) {
    $http.get('/api/about').then(function(response){
      $rootScope.ewall = response.data;
    });
  });


  angular.module('ew').constant('qrCodeParameters', {
    qrcode_margin: 0,
    qrcode_foreground: '#3A3A3A',
    qrcode_background: '#B0BEC5',
    qrcode_colour_hover: '#2196f3',
    qrcode_colour_text: '#2196f3'
  });

  angular.module('ew').constant('USER_ROLES', {
    all: '*',
    admin: 'ADMIN',
    public: 'PUBLIC',
    writer: 'WRITER'
  });
})();

