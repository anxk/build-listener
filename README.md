# Node Notification Plugin

[![Build Status](https://www.travis-ci.org/anxk/node-notification-plugin.svg?branch=master)](https://www.travis-ci.org/anxk/node-notification-plugin)

This plugin allows sending notifications when a node is offline.

## Installation

Clone this repo on your disk, navigate into folder `node-notification-plugin` and run command `mvn verify`, when it completed, upload `target/node-notification.hpi` to the update center of Jenkins and install it.

## Configuration

* Global Configuration
Go to manage > configure > Node Notification Plugin, for example:

<p align="center">
	<img src="images/global-configuration-example.jpg" alt="global-configuration-example.jpg"  width=90% height=90%>
	<p align="center">
		<em>Global Configuration Example</em>
	</p>
</p>

* Node Configuration
Go to node configure page and add node notification properity, for example:

<p align="center">
	<img src="images/node-property-configuration-example.jpg" alt="node-property-configuration-example.jpg"  width=90% height=90%>
	<p align="center">
		<em>Node Properity Configuration Example</em>
	</p>
</p>
