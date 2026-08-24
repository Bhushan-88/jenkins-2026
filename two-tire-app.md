install jenkins 
install plugin :stage view
add agent node(slave) via ssh username and password 

## agent node :
install docker, docker-compose-v2 and java
sudo usermod -aG docker ubuntu (if on master add jenkins user also)
sudo systemctl restart jenkins.service (on master)
add  agent { label "dev"}; in Jenkinsfile
access dev-agent IP:5000
ubuntu@ip-172-31-39-22:~$ sudo chown -R ubuntu jenkins/workspace/two-tire/mysql-data/ (because jenkins has not permission of mysl_data (if on dev-agent change user ubuntu ))

## Now push image on registory Dockerhub
add dockerhub cred in jenkins credentias
seect username and password 
id:dockerHubCreds - and  save
add in pipeline :withcredentials