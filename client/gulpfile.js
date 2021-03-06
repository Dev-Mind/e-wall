var gulp = require('gulp');
//Removes a directory or a file
var del = require('del');
//Run in order
var gulpSequence = require('gulp-sequence');

var paths = {
  build: {
    all: 'build',
    dev: 'build/dev',
    e2e: 'build/e2e',
    dist: 'build/dist'
  },
  js: {
    app: [
      'src/app/index.js',
      'src/app/**/*.js'
    ],
    vendor: [
      'node_modules/angular/angular.js',
      'node_modules/angular-cookies/angular-cookies.js',
      'node_modules/angular-animate/angular-animate.js',
      'node_modules/angular-marked/angular-marked.js',
      'node_modules/angular-sanitize/angular-sanitize.js',
      'node_modules/angular-ui-router/release/angular-ui-router.min.js',
      'node_modules/angular-ui-bootstrap/ui-bootstrap-tpls.min.js',
      'node_modules/moment/moment.js',
      'node_modules/markdown/lib/markdown.js',
      'node_modules/save-svg-as-png/saveSvgAsPng.js'
    ],
    unit: [
      'src/test/unit/**/*.spec.js'
    ],
    e2e: [
      'src/test/e2e/*.spec.js'
    ],
    libe2e: [
      'node_modules/angular-mocks/angular-mocks.js',
      'src/test/e2e/e2eConfig.js',
    ]
  },
  templates: [
    'src/app/component/**/*.html'
  ],
  css: [
    'node_modules/bootstrap/dist/css/bootstrap.min.css'
  ],
  sass: {
    main: 'src/app/index.scss',
    path: [
      'src/app/**/*.scss'
    ]
  },
  html: 'src/app/**/*.html',
  index: 'src/app/index-dev.html',
  assets: {
    fonts: [
      'src/app/assets/fonts/**/*.*',
      'node_modules/bootstrap/dist/fonts/*.*'
    ],
    images: [
      'src/app/assets/img/**/*.*',
      'src/app/assets/logo/*.*'
    ],
    i18n: [
      'src/app/assets/i18n/**/*.*'
    ],
    docs: [
      'src/app/assets/docs/**/*.*'
    ],
    favicon: [
      'src/app/assets/favicon.ico'
    ]
  }
};

var config = {
  paths: paths,
  timestamp: Date.now()
};

require('./gulp/build-dev.js')(gulp, config);
require('./gulp/build-dist.js')(gulp, config);
require('./gulp/build-e2e.js')(gulp, config);
require('./gulp/unit.js')(gulp, config);
require('./gulp/e2e.js')(gulp, config);
require('./gulp/serve.js')(gulp, config);

gulp.task('default', function () {
  gulp.start(['build']);
});

gulp.task('build', function (callback) {
  gulpSequence('clean', 'build:dist', callback);
});

gulp.task('serve', function (callback) {
  gulpSequence('clean', 'serve:dev', callback);
});

gulp.task('clean', function (done) {
  del(paths.build.all, done);
});


module.exports = {
  paths: paths
};