#!/bin/bash

sudo apt-get install -y nginx

sudo apt-get install -y cron

sudo apt-get install -y certbot

sudo service nginx start

sudo systemctl enable nginx.service

sudo service cron start

sudo systemctl enable cron.service

sudo mkdir -p /var/www/letsencrypt/.well-known/acme-challenge /etc/systemd/system/nginx.service.d

sudo su -c 'printf "[Service]\nExecStartPost=/bin/sleep 0.1\n" > /etc/systemd/system/nginx.service.d/override.conf'

sudo su -c 'printf "server {\n\tlisten 80;\n\tserver_name transcendence.dev;\n\n\tlocation ^~/.well-known/acme-challenge/ {\n\t\tdefault_type \"text/plain\";\n\t\troot /var/www/letsencrypt;\n\t}\n}" > /etc/nginx/sites-available/default'

sudo systemctl daemon-reload

sudo systemctl restart nginx

sudo chmod -R 777 /var/www/letsencrypt

sudo certbot certonly --webroot -w /var/www/letsencrypt -d transcendence.dev --agree-tos -m bigpel66@icloud.com
