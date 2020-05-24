# Super Hero Chess
A board game inspired by Chess where each piece is a superhero with a special power.

## Rules
* A turn based 2 Player game where the player who reaches the target payload (7) first wins the game.
* Killing a hero renders a point, killing a sidekick renders half a point.
* In each turn, you muse either chose to use a special power or to move to a cell (either empty cell or to an enemy cell to attack it)
* Checkout this repo's [Wiki](../../wiki/Rules) for the complete rules

| Hero | Allowed Movements| Power
| ------------- |-------------|-----
| Ranged |⬅️⬆️➡️⬇️↖️↘️↙️↗️| Attack an enemy in one of the orthogonal directions provided that no friendly piece lies in the way (does not have to be adjacent)
| Super  |⬅️ ⬆️ ➡️ ⬇️   | Eliminate all enemy pieces that lie within 2 sqaures in any orthogonal direction
| Medic  |⬅️ ⬆️ ➡️ ⬇️| Revive a friendly piece in any adjacent cell
| Tech  |↖️↘️↗️ ↙️ | <ol><li>Restore power of a friendly piece.</li><li>Hack an enemy's piece taking away the chance to use its power.</li><li>Can teleport any friendly piece (othet than itself) anywhere on the board.</li></ol>
|Armored |⬅️⬆️➡️⬇️↖️↘️↙️↗️|Survies the first attack
|Speedster |⬅️⬆️➡️⬇️↖️↘️↙️↗️|Takes two movements in one turn
|Sidekick (Player 1)|	⬇️⬆️➡️↘️↗️| Transofrms into the hero it attacks
|Sidekick (Player 2)|	⬅️⬇️⬆️↖️↙️|Transofrms into the hero it attacks

## Dependencies
* [JavaFX](https://openjfx.io/)
* [BootstrapFX](https://github.com/kordamp/bootstrapfx)

