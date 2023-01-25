#!/bin/bash

sudo apt-get update

apt-get install -y sudo

sudo apt-get install -y apt-transport-https

sudo apt-get install -y ca-certificates

sudo apt-get install -y curl

sudo apt-get install -y wget

sudo apt-get install -y software-properties-common

sudo apt-get install -y git

sudo apt-get install -y make

sudo apt-get install -y vim

sudo apt-get install -y systemd

sudo apt-get install -y vsftpd

wget -O- https://apt.corretto.aws/corretto.key | sudo apt-key add -

sudo add-apt-repository 'deb https://apt.corretto.aws stable main'

sudo apt-get update

sudo apt-get install -y java-17-amazon-corretto-jdk
