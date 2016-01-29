(function () {

  'use strict';
  /*global componentHandler */

  /**
   * Event handlers for errors (internal, security...)
   */
  angular.module('ew').run(function ($rootScope, $state, $timeout, SecurityService, SpinnerService) {
    'ngInject';

    //Error are catched to redirect user on error page
    $rootScope.$on('$ewError', function (event, response) {
      $state.go('ewerror', {error: response});
    });

    //Error are catched to redirect user on login page
    $rootScope.$on('$ewLoginRequired', function (event, response) {
      $state.go('login');
    });

    //When a ui-router state change we watch if user is authorized
    $rootScope.$on('$stateChangeStart', function (event, next) {
      SpinnerService.wait();
      SecurityService.valid(next.authorizedRoles);
    });
    $rootScope.$on('$stateChangeError', function () {
      SpinnerService.stopWaiting();
    });
    $rootScope.$on('$stateChangeSuccess', function (event, next) {
      //In bigQRcode spinner is managed in controller
      if(next.url.indexOf('bigqrcode')<0){
        SpinnerService.stopWaiting();
      }
    });
  });

})();
