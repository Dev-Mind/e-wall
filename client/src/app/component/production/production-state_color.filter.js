(function () {

  'use strict';

  angular.module('ew-admin').filter('stateColor', function (STATE) {
    'ngInject';

    return function(input){
      if(input){
        switch(input){
          case STATE.PENDING:
            return 'label-default';
          case STATE.VALIDATED:
            return 'label-primary';
          case STATE.CENSORED:
            return 'label-danger';
        }
      }
      return '';
    };
  });

})();