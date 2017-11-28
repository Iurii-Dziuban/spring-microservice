#!/bin/sh
# After maven build -> install user-service rpm (after removing already installed version)
sudo rpm -q user-service-rpm
if [ $? -eq 0 ]
then
  sudo rpm -e --allmatches user-service-rpm
fi
sudo rpm -i `find user-service-rpm/target/rpm -name \*.rpm`
