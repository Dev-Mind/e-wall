(function () {

  'use strict';

  angular.module('nw-admin').controller('BigQRCodeCtrl', function ($http) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      $http
        .get('/api/category')
        .then(function(response){
          ctrl.categories = response.data;
        });
    }

    //ctrl.refresh = function(){
    //  $http.get('/api/category/' + ctrl.id)
    //    .then(function (response) {
    //      ctrl.entity = response.data;
    //
    //      ctrl.svg = response.data.qrcodes.filter(function(elt){
    //        return elt.big===true;
    //      })[0];
    //
    //      ctrl.qrs = response.data.qrcodes
    //        .filter(function(elt){
    //          return elt.big!==true;
    //        });
    //
    //      ctrl.qrs.forEach(function(elt){
    //          elt.x = elt.x*29+6;
    //          elt.y = elt.y*29+6;
    //        });
    //    });
    //};

    refresh();
  });

})();