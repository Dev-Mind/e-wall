(function () {

  'use strict';

  angular.module('nw-admin').controller('BigQRCodeCtrl', function ($http, $timeout) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      $http
        .get('/api/category')
        .then(function(response){
          ctrl.categories = response.data;
          if(ctrl.categories && ctrl.categories.length>0){
            ctrl.selectCategory(ctrl.categories[0].id)
          }
        });
    }

    ctrl.selectCategory = function(id){
      $http.get('/api/category/' + id)
        .then(function (response) {
          ctrl.qrs = response.data.qrcodes
            .filter(function(elt){
              return elt.big!==true;
            });

          var mainQr = response.data.qrcodes
            .filter(function(elt){
              return elt.big===true;
            })[0];

          $timeout(function(){
            var svg = document.querySelector('.bigqrcode');
            var dimension = svg.clientHeight>svg.clientWidth ? svg.clientWidth : svg.clientHeight;

            var ratio = dimension/(mainQr.dimension+4);

            ctrl.qrs.forEach(function(elt){
              elt.x = elt.x*ratio+6;
              elt.y = elt.y*ratio+6;
              elt.scaleDimension = ratio;
              elt.scaleRatio = ratio/elt.dimension;
            });
          });

        });
    };
    //ctrl.refresh = function(){


    refresh();
  });

})();