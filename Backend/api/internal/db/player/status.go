package player

import "github.com/Hackathon-for-FUN-TeamA/backend/internal/db"

// Playerのstatusを更新する
func UpdateStatus(id, status int) error {
	// db初期化
	dbmap, err := db.InitDb()
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
	_, err = dbmap.Update(&playerData)
	if err != nil {
		return err
	}

	return nil
}
