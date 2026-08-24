install jenkins 
install plugin :stage view
add agent node(slave) via ssh username and password 

agent node :
install docker, docker-compose-v2 and java
sudo usermod -aG docker ubuntu (if on master add jenkins user also)
sudo systemctl restart jenkins.service (on master)
add  agent { label "dev"}; in Jenkinsfile
access dev-agent IP:5000
