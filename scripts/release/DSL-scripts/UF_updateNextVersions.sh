TARGET_USER=kiereleaseuser
TARGET_USER_REMOTE=kie
REMOTE_URL=git@github.com:kiereleaseuser/uberfire.git
DATE=$(date "+%Y-%m-%d")

# clone the repository and branch for uberfire and uberfire-extensions
git clone git@github.com:uberfire/uberfire.git --branch $BASE_BRANCH
cd $WORKSPACE/uberfire
PR_BRANCH=uberfire-$DATE-$BASE_BRANCH
git checkout -b $PR_BRANCH $BASE_BRANCH
git remote add $TARGET_USER_REMOTE $REMOTE_URL

#UBERFIRE
# upgrades the version to next development version of Uberfire
sh scripts/release/update-version.sh $newVersion

# git add and commit the version update changes 
git add .
commitMSG="update to next development version $newVersion"
git commit -m "$commitMSG"

# do a build of uberfire
mvn -B -e -U clean install -Dmaven.test.failure.ignore=true -Dgwt.memory.settings="-Xmx2g -Xms1g -Xss1M"

# Raise a PR
SOURCE=uberfire
git push $TARGET_USER_REMOTE $PR_BRANCH
hub pull-request -m "$commitMSG" -b $SOURCE:$BASE_BRANCH -h $TARGET_USER:$PR_BRANCH
