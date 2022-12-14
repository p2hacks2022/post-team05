package main

import (
	"github.com/gin-gonic/gin"
)

func Ping(c *gin.Context) {
	c.JSON(200, gin.H{
		"message": "pong",
	})
}

func UpdatePlayerStatus(c *gin.Context) {
	id := c.Param("id")
	status := c.Param("status")

	player.UpdateStatus(id, status)
}
