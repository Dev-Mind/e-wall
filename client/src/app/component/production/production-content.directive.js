(function () {

  'use strict';

  angular.module('ew-structure').directive('productionContent', function () {
    'ngInject';

    return {
      templateUrl: 'component/production/production-content.directive.html'
    };
  });
})();