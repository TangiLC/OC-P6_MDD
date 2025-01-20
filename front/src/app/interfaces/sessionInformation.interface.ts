export interface SessionInformation {
  token: string;
  id: number;
  username: string;
  email: string;
  isAdmin: boolean;
  themes: number[];
  comments: number[];
}
