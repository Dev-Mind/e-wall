(function () {

  'use strict';

  angular.module('ew-structure').filter('ewDate', function () {
    'ngInject';

    return function (input) {
      if(input){
        if (angular.isArray(input) && input.length==6) {
          return moment({y: input[0], M: input[1]-1, d: input[2], h: input[3], m: input[4], s: input[5]}).format('DD/MM/YYYY');
        }
        else{
          return 'Invalid date';
        }
      }
      return '';
    };
  });

})();