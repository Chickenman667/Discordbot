# File Name: utils.py

import discord
from discord.ext import commands

# Utility functions for the bot can be added here

def check_permissions(ctx, permission):
    return ctx.author.guild_permissions.administrator
