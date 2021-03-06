//Prefix CSS with Autoprefixer to be compatible with all browsers
var autoprefixer = require('gulp-autoprefixer');
//Concats files
var concat = require('gulp-concat');
//Convert HTML teplates in JS
var html2js = require('gulp-ng-html2js');
//Compiles sass file in css file
var sass = require('gulp-sass');
//Merges several files
var merge = require('merge-stream');
//Changes angular files to prepare minification
var ngAnnotate = require('gulp-ng-annotate');
//Rename a file
var rename = require('gulp-rename');
//Replaces element in file
var replace = require('gulp-replace');
//Writes inline source maps
var sourcemaps = require('gulp-sourcemaps');
//several class utils for gulp
var utils = require('gulp-util');


module.exports = function(gulp, config) {
  var paths = config.paths;

  gulp.task('build:dev', [
    'build:dev:vendors',
    'build:dev:js',
    'build:dev:css',
    'build:dev:font',
    'build:dev:images',
    'build:dev:favicon',
    'build:dev:html'
  ]);


  gulp.task('build:dev:font', function () {
    return gulp.src(paths.assets.fonts)
      .pipe(gulp.dest(paths.build.dev + '/fonts'));
  });

  gulp.task('build:dev:images', function () {
    return gulp.src(paths.assets.images)
      .pipe(gulp.dest(paths.build.dev + '/img'));
  });

  gulp.task('build:dev:favicon', function () {
    return gulp.src(paths.assets.favicon)
      .pipe(gulp.dest(paths.build.dev));
  });

  gulp.task('build:dev:css:vendors', function () {
    return gulp.src(paths.css)
      //In Angular Material Lite we don't use the standard primary color
      .pipe(replace('"Helvetica Neue",Helvetica,Arial,sans-serif', '"Roboto","Arial"'))
      .pipe(replace('Menlo,Monaco,Consolas,"Courier New",monospace', 'monospace'))
      .pipe(replace('#337ab7', '#522b93'))
      .pipe(replace('#2e6da4', '#2A095F'))
      .pipe(replace('#286090', '#2A095F'))

      .pipe(concat('vendors.css'))
      .pipe(gulp.dest(paths.build.dev+ '/css'));
  });

  gulp.task('build:dev:css', ['build:dev:css:vendors'], function () {
    return gulp.src(paths.sass.main)
      .pipe(sass().on('error', sass.logError))
      .pipe(replace('assets/img', '../img'))
      .pipe(autoprefixer({
        browsers: ['> 5%'],
        cascade: false
      }))
      .pipe(gulp.dest(paths.build.dev + '/css'));
  });

  /**
   * build:dev JS : concats generated templates and javascript files
   */
  gulp.task('build:dev:js', function() {

    var tpl = gulp.src(paths.templates)
      .pipe(html2js({
        moduleName: 'ew-templates',
        prefix: 'component/'
      }));

    var app = gulp.src(paths.js.app)
      .pipe(ngAnnotate({
        'single_quotes': true,
        add: true
      }));

    return merge(app, tpl)
      .pipe(sourcemaps.init())
      .pipe(concat('index.js'))
      .pipe(sourcemaps.write('.'))
      .pipe(gulp.dest(paths.build.dev + '/js'));
  });

  /**
   * Concatenate js vendor libs
   */
  gulp.task('build:dev:vendors', function () {
    return gulp.src(paths.js.vendor)
      .pipe(concat('vendors.js'))
      .pipe(gulp.dest(paths.build.dev + '/js'));
  });

  gulp.task('build:dev:html', function () {
    gulp.src(paths.index)
      .pipe(rename('index.html'))
      .pipe(gulp.dest(paths.build.dev));
    return gulp.src(paths.html)
      .pipe(gulp.dest(paths.build.dev));
  });

};
