export interface UserInformation {
  id: number;
  username: string;
  email: string;
  picture?: string;
  isAdmin: boolean;
  themeSet: number[];
  commentSet: number[];
}
