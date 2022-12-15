package main

import (
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()

	// sample
	r.GET("/ping", Ping)

	// sample
	r.POST("/throw", func(c *gin.Context) {
		username := c.PostForm("username")
		password := c.PostForm("password")
		c.JSON(200, gin.H{
			"username": username,
			"password": password,
		})
	})

	// プレイヤーの状態を更新するAPI
	r.POST("/api/v1/players/:id/status/:status", UpdatePlayerStatus)

	// 127.0.0.0:8000でサーバを建てる
	r.Run()
}
