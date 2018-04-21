package models

type User struct {
	modelImpl
	UserName string
	FullName string
	Email    string
}

func NewUser(userName, fullName, email string) *User {
	u := &User{
		UserName: userName,
		FullName: fullName,
		Email:    email,
	}
	u.SetId(userName)
	return u
}

func (u *User) GetId() string {
	return u.UserName
}
