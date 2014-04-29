TAG_NAME=$1
TAG_COMMENT=$2

git tag -a "$1" -m "$2"
git push origin "$1"
