export WORK_DIR=/mnt/new
mkdir $WORK_DIR
cd $WORK_DIR
wget http://files.grouplens.org/datasets/movielens/ml-1m.zip
gunzip $WORK_DIR/ml-1m.zip