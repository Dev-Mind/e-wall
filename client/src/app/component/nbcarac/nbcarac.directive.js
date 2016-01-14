(function () {

  'use strict';

  angular.module('ew-admin').directive('ewNbCarac', function () {
    'ngInject';

    return {
      templateUrl: 'component/nbcarac/nbcarac.directive.html',
      scope: {
        nbcarac : '@',
        text : '=',
        field : '='
      }
    };
  });
})();