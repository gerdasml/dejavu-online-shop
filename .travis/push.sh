#!/bin/sh

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
    git remote add prod ssh://deploy@159.89.106.85:/srv/deploy/dejavu.git > /dev/null 2>&1
    git push --quiet --set-upstream prod master 
}

setup_git
commit_website_files
upload_files