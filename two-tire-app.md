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
add in pipeline - withcredentials:

 ## add webhook URL github repo settings
 http://100.48.18.248:8080/github-webhook/
 In jenkins pipeline ->configure ->GitHub project ->add github repo url And select trigger ->GitHub hook trigger for GITScm polling (It will trigger automatically when any changes happen in code)

 ## RBACK
Install plugin - Role-based Authorization Strategy 
Goto:manage jenkins ->Security->Authorization->Role-based stategy
Next:Manage Jenkins->Role Management->Manage role->Role to add->name of role and access

## add email-Notification
1.add SMTPS port 465 in sec grp
Goto :google-Manage your Google Account-enable two-stp-verification
2.search app password in Manage your Google Account
create app password
Copy pass=wixs evvy rfwt qggx
3.Add gmail and app pass in Jenkins credentials->username and password
4.find Manage Jenkins->System->SMTP

SMTP server:
smtp.gmail.com
SMTP Port:
465
Advanced
Credentials:
bhushandurgawli1@gmail.com/****** (this is my gmail pass)
