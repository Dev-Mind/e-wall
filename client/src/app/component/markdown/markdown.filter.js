(function () {

  'use strict';

  angular.module('ew-structure').filter('markdown', function ($sanitize) {
    'ngInject';

    return function(input){
      if(!input){
        return undefined;
      }
      return $sanitize(window.markdown.toHTML(input));
    };
  });

})();
