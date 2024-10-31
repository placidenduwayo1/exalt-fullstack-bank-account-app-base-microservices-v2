export class JwtDto {
    username!: string;
    pwd!: string;
    grantType!: string;
    withRefreshToken!: boolean;
    refreshToken!: string;
}