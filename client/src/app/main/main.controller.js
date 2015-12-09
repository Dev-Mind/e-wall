(function () {

  'use strict';

  /**
   * member is resolved in app.js
   */
  angular.module('nw-structure').controller('MainCtrl', function () {
    'ngInject';

    var ctrl = this;

    ctrl.squares=[];
    for(var i=0 ; i<100 ; i++){
      ctrl.squares.push(i);
    }

  });
})();
