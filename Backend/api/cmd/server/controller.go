package main

import (
	"strconv"

	"github.com/Hackathon-for-FUN-TeamA/backend/internal/db/player"
	"github.com/gin-gonic/gin"
)

func Ping(c *gin.Context) {
	c.JSON(200, gin.H{
		"message": "pong",
	})
}

// プレイヤーの状態を更新する
func UpdatePlayerStatus(c *gin.Context) {
	// パスパラメータ取得
	id, err := strconv.Atoi(c.Param("id"))
	if err != nil {
		c.JSON(500, gin.H{
			"message": "idが違います",
		})
		return
	}
	status, err := strconv.Atoi(c.Param("status"))
	if err != nil || status < 1 || 4 < status {
		c.JSON(500, gin.H{
			"message": "statusが違います",
		})
		return
	}

	// status更新
	player.UpdateStatus(id, status)
}
