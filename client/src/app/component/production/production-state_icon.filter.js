(function () {

  'use strict';

  angular.module('ew-admin').filter('stateIcon', function (STATE) {
    'ngInject';

    return function(input){
      if(input) {
        switch (input) {
          case STATE.PENDING:
            return 'glyphicon glyphicon-cloud-upload';
          case STATE.VALIDATED:
            return 'glyphicon glyphicon-ok';
          case STATE.CENSORED:
            return 'glyphicon glyphicon-exclamation-sign';
        }
      }
      return '';
    };
  });

})();