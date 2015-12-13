(function () {

  'use strict';

  angular.module('nw-admin').controller('BigQRCodeCtrl', function ($http, $timeout, $window, qrCodeParameters) {
    'ngInject';

    var ctrl = this;

    //We read the user preference
    var json = $window.localStorage.getItem('parameters');
    if(!json){
      ctrl.parameterMap = qrCodeParameters;
    }
    else{
      ctrl.parameterMap = angular.fromJson(json);
    }

    function refresh(){
      $http
        .get('/api/category')
        .then(function(response){
          ctrl.categories = response.data;
          if(ctrl.categories && ctrl.categories.length>0){
            ctrl.selectCategory(ctrl.categories[0].id);
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

            //The svg dimension is computed with the window size
            var h  = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight)*0.83;
            var w  = (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth)*0.83;
            var dimension = h > w ? w : h;
            svg.style.height = dimension;
            svg.style.width = dimension;

            var ratio = dimension/mainQr.dimension;

            ctrl.text = {
              size : mainQr.dimension/2 * 1.5* ratio + 'px',
              x:dimension/4,
              y:dimension - dimension/4,
              content:response.data.message
            };

            ctrl.qrs.forEach(function(elt){
              elt.x = elt.x*ratio;
              elt.y = elt.y*ratio;
              elt.scaleDimension = ratio;
              elt.scaleRatio = ratio/elt.dimension;
            });
          });

        });
    };

    refresh();
  });

})();