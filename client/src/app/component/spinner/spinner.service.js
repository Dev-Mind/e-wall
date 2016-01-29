(function () {

  'use strict';

  /**
   * This service is used to display a spinner when a request is executed
   */
  angular.module('ew-structure').factory('SpinnerService', function ($rootScope, $timeout) {
    'ngInject';

    var waitingPopupTimeout;
    $rootScope.waitingPopup = false;

    function wait(timing) {
      if(!waitingPopupTimeout){
        waitingPopupTimeout = $timeout(function(){
          //document.location.href='#top';
          $rootScope.waitingPopup = true;
        }, timing ? timing : 100);
      }
    }

    function stopWaiting() {
      $rootScope.waitingPopup = false;
      if(waitingPopupTimeout){
        $timeout.cancel(waitingPopupTimeout);
        waitingPopupTimeout=undefined;
      }
    }

    return {
      activate: function(){
        $rootScope.spinner = 'on';
      },

      desactivate: function() {
        $rootScope.spinner = 'off';
      },

      wait:wait ,
      stopWaiting:stopWaiting
    };
  });

})();
