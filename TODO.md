Issue 1:
When a user goes to /login with already valid refresh token.

## Current behaviour
Empty response as repository.save will give duplicate key error

## To do
Should delete previous refresh token and generate and save new one. 