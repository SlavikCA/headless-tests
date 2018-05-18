Headless tests
------

This is experiment to run Selenium tests on remote Linux host.

Without Selenium Grid.
Without always-on running Selenium server.

Here is the Linux system I used to run test:
- CentOS 7.5
- Firefox 60
- Chrome 66

And to write test, I used:
- TestNG 6.14
- Selenium 3.12
- Groovy 2.3
- Gradle

Steps to configure OS:
-----
- Install Minimal CentOS 7.5
- Configure it's network: 
  - `sudo nmtui`
  - `service network restart`
  - `ip a`
- Install some basic software:
  - `sudo yum install rsync`
  - `sudo systemctl enable rsyncd`
  - `sudo yum install nano`
  - `sudo yum install java-1.8.0-openjdk-headless`
  
  Install Apache and open firewall 
  - `sudo yum install httpd`
  - `sudo systemctl enable httpd.service`
  - `sudo firewall-cmd --add-service=http --permanent && sudo firewall-cmd --add-service=https --permanent`
  - `sudo systemctl restart firewalld`
  - `sudo systemctl restart httpd.service`

? permissions for test results ?
 
- Don't install Firefox via `yum`, because it's old (v 52. it doesn't support headless mode)
- Install Firefox by downloading from Mozilla:
  - `cd /opt/`
  - `sudo wget https://download-installer.cdn.mozilla.net/pub/firefox/releases/60.0.1/linux-x86_64/en-US/firefox-60.0.1.tar.bz2`
  - `sudo yum install bzip2`
  - `sudo tar xfj firefox-60.0.1.tar.bz2`
  - `sudo ln -s /opt/firefox/firefox /usr/bin/firefox`
  - `firefox --version`
- Install Chrome:
  - `sudo vi /etc/yum.repos.d/google-chrome.repo`

paste:
```yaml
[google-chrome]
name=google-chrome
baseurl=http://dl.google.com/linux/chrome/rpm/stable/$basearch
enabled=1
gpgcheck=1
gpgkey=https://dl-ssl.google.com/linux/linux_signing_key.pub
```

  - `sudo yum install google-chrome-stable`
  - `chrome --version`