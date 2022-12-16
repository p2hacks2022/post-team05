package player

import (
	"github.com/p2hacks2022/post-team05/internal/model"
)

// Playerのstatusを更新する
func UpdateStatus(id, status int) error {
	// db初期化
	dbmap, err := model.InitDb()
	if err != nil {
		return err
	}
	defer dbmap.Db.Close()

	// dbからプレイヤーを取得
	playerData := Player{}
	err = dbmap.SelectOne(&playerData, "SELECT * FROM `players` WHERE `id` = ?", id)
	if err != nil {
		return err
	}

	// status更新
	playerData.Status = status
	dbmap.AddTableWithName(Player{}, "players").SetKeys(true, "Id")
	_, err = dbmap.Update(&playerData)
	if err != nil {
		return err
	}

	return nil
}
