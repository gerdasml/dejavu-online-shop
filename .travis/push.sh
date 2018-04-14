#!/bin/sh

set -x # verbose mode

setup_git() {
    git config --global user.email "travis@travis-ci.org"
    git config --global user.name "Travis CI"
}

commit_project_files() {
    git checkout -b master
    git add .
    git commit --message "Travis build: $TRAVIS_BUILD_NUMBER"
}

upload_files() {
    git remote add prod ssh://deploy@159.89.106.85:/srv/deploy/dejavu.git
    git push --set-upstream prod master  -f
}

setup_git
commit_project_files
upload_files